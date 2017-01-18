; RESTful server to provide albums of files  A. Gardner 2016

(ns web-ring-2.handler
  (:use compojure.core
        ring.middleware.json
        web-ring-2.route
        ring.middleware.logger
        clj-logging-config.log4j)
  (:require 
    [compojure.handler :as handler]))

(defroutes app-routes
           (GET "/" [] 
                (list-albums))
           (GET "/:album" [album] 
                (list-photos album))
           (GET "/:album/:photo" [album photo] 
                (download-photo album photo))
           (PUT "/:album/:photo" [album photo alias] 
                (set-alias! album photo alias)))
            
(def app (-> app-routes handler/api wrap-json-response wrap-with-logger))
                                                                                             