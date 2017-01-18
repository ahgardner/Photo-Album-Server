; Persistence for the Wedding Pictures service  A. Gardner 2017

; Second version: database

(ns web-ring-2.dbdata (:require [web-ring-2.config :as cfg]))

(use 'clojure.java.jdbc)
 
(def db-create-cmd
  "create table if not exists pictures (pic_id varchar(256) primary key, alias varchar(20))") 

(defn define-table! [db-con]
  (db-do-commands db-con false db-create-cmd))
  
(defn get-alias [key db-con] "Get an alias"
  (define-table! db-con)
  (let [rs (query db-con ["select alias from pictures where pic_id = ?" key])]
    (when (not (empty? rs))
      (:alias (first rs)))))

(defn set-alias! [path alias] "Set the picture alias"
  (with-db-connection [db-con (cfg/dbdefcfg)]
    (define-table! db-con)
    (let [rs (query db-con ["select * from pictures where pic_id = ?" path])]
      (if (empty? rs)
        (execute! db-con ["insert into pictures (pic_id, alias) values (?, ?)" path alias])
        (execute! db-con ["update pictures set alias = ? where pic_id = ?" alias path])))))
  
(defn properties [path db-con image] "Get the full info for an image"
  (let [file-path (str path "/" image)
        alias (get-alias file-path db-con)]
    {:key image :name alias}))
  
(defn merge-properties [path image-list] "Transform file list to map list"
  (with-db-connection [db-con (cfg/dbdefcfg)]
                      (vec (map (partial properties path db-con) image-list))))
