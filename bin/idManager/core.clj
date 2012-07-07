(ns idManager.core
  (:require [noir.server :as server])
  (:require [noir.response :as response])
  (:require [noir.validation :as vali])
  (:use [noir.core :only [defpage defpartial]])
  (:use [hiccup.page :only [html5 include-css include-js]])
  (:use [hiccup.element])
  (:use [hiccup.form])
  (:use [hiccup.util :only [escape-html]])
  (:require [idManager.domain :as domain]))

(defn -main [& m]
  (let [mode (or (first m) :dev)
        port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode (keyword mode)
                        :ns 'noir})))

(defpartial display-id [ {:keys [id website userid password]} ]
  [:tr 
   [:td website]
   [:td userid]
   [:td password]
  ])

(defpartial error-text [[first-error]]
  [:p.error first-error])
  
(defpartial left-side-content [{:keys [website userid password]}]
  (form-to {:class "form-horizontal"} [:post "/add"]
                 [:fieldset
                  [:legend "Add Webapp - Id " ]
                  [:div.control-group
                  (label {:for "url"} "urllbl" "Website URL")
                  (text-field {:class "input-xlarge" :size 55 :maxlength 55} "url" website )
                  (vali/on-error :website error-text)
                  ]
                  [:div.control-group
                  (label {:for "userid"}  "uidlbl" "User Id")
                  (text-field {:class "input-xlarge" :size 25 :maxlength 20} "userid" userid)
                  (vali/on-error :userid error-text)
                  ]
                  [:div.control-group
                  (label {:for "password"}  "pwdlbl" "Password ")
                  (password-field {:class "input-xlarge" :size 15 :maxlength 15 } "password" password)
                  (vali/on-error :password error-text)
                  ]
                  [:div.form-actions
                  (submit-button {:class "btn btn-primary"} " Save ")
                  (escape-html "    ")
                  (reset-button {:class "btn btn-primary"} " Cancel ")
                  ]
                  
                 ]) )

(defpartial right-side-content [] 
  [:h3 "List of Webapp Ids"]
        [:table.table.table-bordered.table-striped
         [:th "Webapp/Site "] [:th "User Id"] [:th "Password "]
         (map display-id (domain/get-all))
        ]
  )

(defpage "/" [] 
  (html5 
    [:head
     [:style
      (escape-html "body{padding-top:60px}")]
     [:title "Manage your webapps-id"]
     (include-css "bootstrap.css")]
     
    [:body
      [:div.navbar.navbar-fixed-top 
       [:div.navbar-inner
        [:div.container
         (link-to {:class "brand"} "#" "Webapp-id Store")
         [:div.nav-collapse
          [:ul.nav
           [:li.active (link-to "#" "Home")]
           [:li (link-to "#about" "About")]
           [:li (link-to "#contact" "Contact")]
           ]
          ]
         ]
        ]
       ]
      [:div.row
       [:div.span1 
        [:h3 "&nbsp" ] 
       ]
       [:div.span7
        (left-side-content {})
       ]
       [:div.span5
        (right-side-content)
       ]
      ]
    ]))
     
     
(defn valid? [{:keys [url userid password]}]
  (vali/rule (vali/has-value? password)
             [:password "Please enter password"])
  (vali/rule (vali/min-length? password 8)
             [:password "Password length is too short"])
  (vali/rule (vali/has-value? url)
             [:url "Please enter Webapp Url"])
  (vali/rule (vali/has-value? userid)
             [:userid "Please enter Userid"])
   (not (vali/errors? :url :password :userid))) 
    
    
(defpage [:post "/add"] {:keys [url userid password] :as entry}
  (when (valid? entry)
    (domain/add-new-id url userid password))
  (response/redirect "/" entry))
  

  

(defn check [] 
  (print "Working "))


