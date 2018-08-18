(ns todo-list.worms
  (:require 
   [compojure.core :refer [defroutes GET]]
))

;worm data
(defstruct owner :name :wormbins)
(defstruct wormbin :name :feedings)
(defstruct feeding :date :time :weight :section :picture)


(def feeding1 (struct feeding "29.07.2018" "10:00" 200 "Q1" ))
(def feeding2 (struct feeding "29.07.2018" "12:00" 175 "Q2" ))
(def feeding3 (struct feeding "30.07.2018" "08:00" 250 "Q3" ))
(def wb1 (struct wormbin "Hungry Bin" [feeding1 feeding2 feeding3] ))
(def chris (struct owner "Christian" [wb1]))

(defn bins
  [request]
  {:status 200
   :body (str wb1) })

(defn owners
  [request]
  {:status 200
   :body (str chris) })

(defroutes myname
  (GET "/bins" [] bins)
  (GET "/owners" [] owners)
)
