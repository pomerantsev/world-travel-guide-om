(defproject simple-js-app-om "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2850"]
                 [figwheel "0.2.5-SNAPSHOT"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [sablono "0.3.4"]
                 [org.omcljs/om "0.8.8"]
                 [cljs-http "0.1.30"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-figwheel "0.2.5-SNAPSHOT"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src" "dev_src"]
              :compiler {:output-to "resources/public/js/compiled/simple_js_app_om.js"
                         :output-dir "resources/public/js/compiled/out"
                         :optimizations :none
                         :main simple-js-app-om.dev
                         :asset-path "js/compiled/out"
                         :source-map true
                         :source-map-timestamp true
                         :cache-analysis true }}
             {:id "min"
              :source-paths ["src"]
              :compiler {:output-to "resources/public/js/compiled/simple_js_app_om.js"
                         :main simple-js-app-om.core
                         :optimizations :advanced
                         :pretty-print false}}]}

  :figwheel {
             :http-server-root "public" ;; default and assumes "resources"
             :server-port 3449 ;; default
             :css-dirs ["resources/public/css"] ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is simple ring servers, if this
             ;; doesn't work for you just run your own server :)
             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             })
