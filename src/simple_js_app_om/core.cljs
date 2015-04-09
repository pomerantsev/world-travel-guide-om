(ns ^:figwheel-always simple-js-app-om.core
    (:require-macros [cljs.core.async.macros :refer [go]])
    (:require [om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]
              [cljs.core.async :refer [<!]]
              [cljs-http.client :as http]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {}))

(defn update-country [owner]
  (om/set-state! owner :visible false)
  (let [country-name (.country (js/Chance.) #js {:full true})
        encoded-country-name (js/encodeURIComponent country-name)]
    (go (let [response (<! (http/get
                             (str "https://country-images.herokuapp.com/image?q="
                                  encoded-country-name)
                             {:with-credentials? false}))]
          (om/set-state! owner :image-src "")
          (js/requestAnimationFrame
            (fn []
              (om/set-state! owner :country-name country-name)
              (om/set-state! owner :image-src (:url (:body response)))
              (om/set-state! owner :visible true)))))))

(defn app-view [data owner]
  (reify
    om/IInitState
    (init-state [_]
      {:country-name ""
       :image-src ""
       :visible false})
    om/IWillMount
    (will-mount [_]
      (update-country owner))
    om/IRenderState
    (render-state [this state]
      (dom/div nil
        (dom/div #js {:style #js {:opacity (if (:visible state) 1 0)}}
          (dom/h2 nil (:country-name state))
          (dom/div nil
            (dom/img #js {:className "country-image"
                          :src (:image-src state)})))
        (dom/button #js {:className "update-button"
                         :onClick #(update-country owner)}
          "Update")))))

(om/root app-view app-state
  {:target (.-body js/document)})


