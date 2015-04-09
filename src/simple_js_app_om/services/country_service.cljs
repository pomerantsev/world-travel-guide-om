(ns ^:figwheel-always simple-js-app-om.services.country-service
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<! >! chan]]
            [cljs-http.client :as http]))

(defn update-country []
  (let [country-name (.country (js/Chance.) #js {:full true})
        encoded-country-name (js/encodeURIComponent country-name)]
    (go (let [response (<! (http/get
                             (str "https://country-images.herokuapp.com/image?q="
                                  encoded-country-name)
                             {:with-credentials? false}))]
          {:country-name country-name
           :image-src (:url (:body response))}))))
