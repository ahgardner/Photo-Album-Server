(defproject Photo-Album-Server "0.1.0-SNAPSHOT"
  :description "RESTful server for a photo album app"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-json "0.2.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler web-ring-2.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
