(ns idManager.domain
  (:require [monger.collection :as monger])
  (:import [org.bson.types ObjectId]))


(defn get-all []
  (monger/find-maps "userids"))


  
(defn add-new-id [url userid pwd]
  (monger/insert "userids" { :url url :userid userid :password pwd })
)

(defn find-by-id [oid]
   (monger/find-map-by-id "userids" (ObjectId. oid)) )


(defn update-id [id url userid pwd]
  (let [ entry {:url url :userid userid :password pwd}
         oid (ObjectId. id)]
    (monger/update-by-id "userids" oid entry)))


(defn delete-id [id] 
  (let [oid (ObjectId. id)]
    (monger/remove-by-id "userids" oid)))


