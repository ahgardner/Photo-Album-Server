; Persistence for the Wedding Pictures service

; First version: a file, no locking

(ns web-ring-2.data 
  (:require 
    [clojure.edn :as edn]
    [clojure.java.io :as io]))

(def filename "wedding-pix-index.edn")

(defn get-index [] "Load the file as a map"
  (if (.exists (io/as-file filename))
    (edn/read-string (slurp filename))
    {}))

(defn put-index! [index] "Save the index as a file"
  (spit filename (prn-str index)))
  
(defn set-alias! [path alias] "Update the file alias"
  (let [new-index (assoc (get-index) path alias)]
    (put-index! new-index)))

(defn properties [path index image] "Get map for one file"
  (let [file-path (str path "/" image)]
    {:key image :name (get index file-path)}))
    
(defn merge-properties [path image-list] "Transform file list to map list"
  (let [index (get-index)]
    (vec (map (partial properties path index) image-list))))
    

   
   
