(ns todo-list.handlers
  (:require 
   [compojure.core :refer [defroutes GET]]
   [ring.handler.dump :refer [handle-dump]] 
   [compojure.route :refer [not-found]]
   [clojure.string :refer [capitalize]]
   [todo-list.views.layout :as layout]
   )
  (:use
   [hiccup.core]
   [hiccup.page]
   [hiccup.bootstrap.page]
   [hiccup.bootstrap.middleware]
   [ring.middleware.resource]))

(defn welcome
  "A ring handler to process all requests sent to the webapp."
  [request]
  (html [:h1 "Hello, Clojure World"]
        [:p "Welcome to your first Clojure app, I now update automatically"]))

(defn goodbye
  "A song to wish you goodbye"
  [request]
  (html5 {:lang "en"}
         [:head (include-js "myscript.js") (include-css "mystyle.css")] 
         [:body 
          [:div [:h1 "Walking back to happiness"]]
          [:div [:p "Walking back to happiness with you"]]
          [:div [:p "Said, Farewell to loneliness I knew"]]
          [:div [:p "Laid aside foolish pride"]]
          [:div [:p "Learnt the truth from tears I cried"]]]))

(defn about
  "Information about the website developer"
  [request]
  {:status 200    
   :body "I am an awesome Clojure developer, well getting there..."  
   :headers {}})

(defn hello
  "A simple personalised greeting showing the use of variable path element"
  [request]

  (let [name (get-in request [:route-params :name])]
    {:status 200    
     :body (str "Hello " name ". I got your name from the web URL")  
     :headers {}}))

(def operands {"+" + "-" - "*" * ":" /})

(defn calculator
  "A list calculator"
  [request]
  
  (let [op (get-in request [:route-params :op])
        a (Integer. (get-in request [:route-params :a]))
        b (Integer. (get-in request [:route-params :b]))
        f (get operands op)]
    
    (if (false? (nil? f))    
      {:status 200    
       :body (str "(" op " " a " " b ")" " = " (f a b))  
       :headers {}}
      {:status 404
       :body "Sorry, unknown operator.  I only recognise + - * : (: is for division)"
       :headers {}})))

(defstruct food :food-name :weight-gross :weight-net )
(def apple (struct food "apple" 173 144 ))
(def banana (struct food "banana" 249 157))
(def carrot (struct food "carrot" 87 67 ))
(def peal  (struct food "peal" 162 142 ))

(def foods [apple banana carrot peal])

(defn alphabet
  "Overview of available pictures of the alphabet"
  [request]
  (layout/common [:h1  "Alphabet"]
                 [:ul
                  (for [food foods]
                    [:li [:a {:href  (str "alphabet/" (:food-name food))} (capitalize (:food-name food))]])
                  ]))

(defn page [request]
  (let 
      [name (String. (get-in request [:route-params :name]))
       name-capitalized (capitalize name)]
    (layout/common [:h1 name-capitalized ]
                   [:div#carouselExampleSlidesOnly.carousel.slide
                    {:data-ride "carousel"}
                    [:div.carousel-inner 
                     [:div.carousel-item.active
                      [:img.d-block.w-100 {:alt "First slide" :src (str "/images/" name "1.jpg")}]]
                     [:div.carousel-item
                      [:img.d-block.w-100 {:alt "Second slide" :src (str "/images/" name "2.jpg")}]]
                     [:div.carousel-item
                      [:img.d-block.w-100 {:alt "Third slide" :src (str "/images/" name "3.jpg")}]]]] 
                   )))

(defstruct recipe :name :description :ingredients )
(defstruct ingredient :amount :unit :name)
(def kakao-butter (struct ingredient 160 "g" "Kakaobutter"))
(def amaranth-gepufft (struct ingredient 120 "g" "Amaranth, gepufft"))
(def bourbon-vanille (struct ingredient 1 "gestr. TL"  "Bourbon Vanille, gemahlen"))
(def almonds (struct ingredient 40 "g" "geröstete Mandeln, gehackt"))
(def kirschen (struct ingredient 800 "g" "Kirschen, getrocknet"))
(def agavendicksaft (struct ingredient 120 "g" "Agavendicksaft"))
(def almond-mus (struct ingredient 100 "g" "weißes Mandelmus"))
(def kakao (struct ingredient 20 "g" "Kakao"))
(def zimt (struct ingredient 1 "TL" "gemahlener Zimt"))
(def seasalt (struct ingredient 1 "Pr." "Meersalz, jodiert"))

(def best-riegel-recipe (struct recipe "Best Riegel" "Amaranth-Riegel mit Kirsche und Mandel"
                                 [kakao-butter amaranth-gepufft bourbon-vanille almonds kirschen
                                  agavendicksaft almond-mus kakao zimt seasalt]))

(defn best-riegel
  [request] 
  (layout/common
    [:h1 (:name best-riegel-recipe)]
    [:p (:description best-riegel-recipe)]
    [:ul.list-group
     (for [ingredient (:ingredients best-riegel-recipe)]
       [:li.list-group-item (str (:amount ingredient)  " " (:unit ingredient) " " (:name ingredient))])]))


(defroutes base
  (GET "/" [] welcome)
  (GET "/goodbye" [] goodbye)
  (GET "/about" [] about)
  (GET "/request-info" [] handle-dump)
  (GET "/hello/:name" [] hello)
  (GET "/calculator/:op/:a/:b" [] calculator)
  (GET "/alphabet" [] alphabet)
  (GET "/alphabet/:name" [] page)
  (GET "/best_riegel" [] best-riegel)
  (wrap-resource [] "public")
  (wrap-bootstrap-resources [])
  (not-found "<h1>This is not the page you are looking for</h1>
   <p>Sorry, the page you requested wass not found!</p>"))
