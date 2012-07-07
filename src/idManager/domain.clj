(ns idManager.domain)

(comment " The Data Model for IDManager App will be as follow"
         #{{:id no :website " Web site URL" :userid "User ID" :password " Password "}
           {:id no1 :website " Web site URL" :userid "User ID" :password " Password "}
          })

(def user-id-list (atom #{}))

(defn get-all []
  @user-id-list)

(defn get-by-id [id]
  ())

(defn next-id []
  (+ 1 (count @user-id-list)))

  
(defn next-id []
  (+ 1 (count @user-id-list)))
  
(defn add-new-id [webapp id pwd]
  (let [entry {:id (next-id) :website webapp :userid id :password pwd}]
  (swap! user-id-list conj  entry)))

(defn chg-password [id new-pwd]
  ())

(defn chg-id [id webapp id pwd]
  ())

(defn search-webapp [ app-name ]
  ())
