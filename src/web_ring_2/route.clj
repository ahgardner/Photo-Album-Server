; Routing for the wedding pictures service  A. Gardner 2016

(ns web-ring-2.route (:require [web-ring-2.data :as data]
                               [clojure.java.io :as io]
                               [clojure.string :as string]
                               [clojure.edn :as edn]
                               [ring.util.response :as resp]
                               [ring.util.codec :as codec]
                               [compojure.route :as comp-rte]
                               ))

(def root-path (:root-path (edn/read-string (slurp "config.edn"))))

(defn get-path [folder file]
  (str root-path "/" 
       (codec/percent-decode folder) "/" 
       (codec/percent-decode file)))

; Album list

(defn undotted? [s]
  (not= \. (get s 0)))
  
(defn list-albums []
  (let [raw-list (.list (io/file root-path))]
    (resp/response (vec (filter undotted? raw-list)))))

; List images in an album

(def image-types ["jpg" "jpeg" "png"])

(defn image-type? [file]
  (let [suffix (last (string/split file #"\."))]
    (some #{suffix} image-types)))    
                     
(defn list-photos [folder]
  (let [path (str root-path "/" (codec/percent-decode folder))]
    (if (.exists (io/as-file path))
      (let [raw-list (.list (io/file path))
            image-list (filter image-type? raw-list)]
        (resp/response (data/merge-properties path image-list)))
      (comp-rte/not-found "Folder not found"))))

; Download an image
             
(defn download-photo [folder file]
  (let [path (get-path folder file)]
    (if (.exists (io/as-file path))
      (resp/file-response path)
      (comp-rte/not-found "File not found"))))

; Set an alias

(defn set-alias! [dir file alias]
  (let [path (get-path dir file)]
    (if (.exists (io/as-file path))
      (do (data/set-alias! path alias) (resp/response nil))
      (comp-rte/not-found "File not found"))))
      
