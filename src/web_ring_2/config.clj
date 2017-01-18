; Configuration handler for the Photo server  A. Gardner 2017

(ns web-ring-2.config (:require [clojure.edn :as edn]))

(def config-file "config.edn")

(defn config [] (edn/read-string (slurp config-file)))

(defn rootcfg [] (:root-path (config)))

(defn dbdefcfg [] (:db-def (config)))