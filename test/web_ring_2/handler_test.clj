(ns web-ring-2.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [web-ring-2.handler :refer :all]
;            [clojure.data.json :as json]
))

(deftest test-app
  (testing "list-paths route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))))

  (testing "list-files route"
    (let [response (app (request :get "/Ceremony"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))))
         
  (testing "download-file route"
    (let [response (app (request :get "/Ceremony/001.jpg"))]
      (is (= (:status response) 200))
      (is (= (type (:body response)) java.io.File))))
         
  (testing "set alias"
    (let [response (app (request :put "/Ceremony/002.jpg?alias=Matthew"))]
     (is (= (:status response) 200))))
 
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
