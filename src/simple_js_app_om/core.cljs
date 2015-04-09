(ns ^:figwheel-always simple-js-app-om.core
    (:require-macros [cljs.core.async.macros :refer [go]])
    (:require [om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]
              [cljs.core.async :refer [<!]]
              [simple-js-app-om.services.country-service :as country-service]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defn update-country [owner]
  (om/update-state! owner #(merge % {:visible false
                                     :image-src ""}))
  (go (let [{:keys [country-name image-src]} (<! country-service/country-updater)]
        (om/update-state! owner
                          #(merge % {:country-name country-name
                                     :image-src image-src
                                     :visible true})))))

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
    (render-state [_ state]
      (dom/div nil
        (dom/div #js {:style #js {:opacity (if (:visible state) 1 0)}}
          (dom/h2 nil (:country-name state))
          (dom/div nil
            (dom/img #js {:className "country-image"
                          :src (:image-src state)})))
        (dom/button #js {:className "update-button"
                         :onClick #(update-country owner)}
          "Update")))))

(om/root app-view nil
  {:target (.-body js/document)})
