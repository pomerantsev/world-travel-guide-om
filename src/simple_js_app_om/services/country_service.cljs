(ns ^:figwheel-always simple-js-app-om.services.country-service
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<! >! chan]]
            [cljs-http.client :as http]))

(def country-updater (chan))

(go (while true
      (let [country-name (.country (js/Chance.) #js {:full true})
            encoded-country-name (js/encodeURIComponent country-name)
            response (<! (http/get
                           (str "https://country-images.herokuapp.com/image?q="
                                encoded-country-name)
                           {:with-credentials? false}))]
        (>! country-updater
            {:country-name country-name
             :image-src (:url (:body response))}))))
