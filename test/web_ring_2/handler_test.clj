(ns web-ring-2.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [web-ring-2.handler :refer :all]
;            [clojure.data.json :as json]
))

(def OK 200)
(def NOTFOUND 404)

(deftest test-app
  (testing "list-paths route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) OK))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))))

  (testing "list-files route"
    (let [response (app (request :get "/Ceremony"))]
      (is (= (:status response) OK))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))))
         
  (testing "list-files bad path"
    (let [response (app (request :get "/Gobbledygook"))]
      (is (= (:status response) NOTFOUND))))
         
  (testing "download-file route"
    (let [response (app (request :get "/Ceremony/001.jpg"))]
      (is (= (:status response) OK))
      (is (= (type (:body response)) java.io.File))))
         
  (testing "download bad path"
    (let [response (app (request :get "/Ceremony/Gobbledygook"))]
      (is (= (:status response) NOTFOUND))))
           
  (testing "set alias"
    (let [response (app (request :put "/Ceremony/002.jpg?alias=Matthew"))]
     (is (= (:status response) OK))))
 
  (testing "alias bad path"
    (let [response (app (request :put "/Ceremony/Gobble?alias=Matthew"))]
      (is (= (:status response) NOTFOUND))))
           
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) NOTFOUND)))))
