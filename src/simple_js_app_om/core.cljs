(ns ^:figwheel-always simple-js-app-om.core
    (:require [om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {}))

(defn update-country [owner]
  (let [country-name (.country (js/Chance.) #js {:full true})]
    (om/set-state! owner :country-name country-name)))

(defn app-view [data owner]
  (reify
    om/IInitState
    (init-state [_]
      {:country-name ""
       :image-src ""})
    om/IWillMount
    (will-mount [_]
      (update-country owner))
    om/IRenderState
    (render-state [this state]
      (dom/div nil
        (dom/div nil
          (dom/h2 nil (:country-name state)))
        (dom/button #js {:className "update-button"
                         :onClick #(update-country owner)}
          "Update")))))

(om/root app-view app-state
  {:target (.-body js/document)})


