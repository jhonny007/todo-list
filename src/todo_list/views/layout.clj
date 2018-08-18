(ns todo-list.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn include-bootstrap-scripts
  []
  (list  [:script
          {:crossorigin "anonymous",
           :integrity
           "sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo",
           :src "https://code.jquery.com/jquery-3.3.1.slim.min.js"}]
         [:script
          {:crossorigin "anonymous",
           :integrity
           "sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49",
           :src
           "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"}]
         [:script
          {:crossorigin "anonymous",
           :integrity
           "sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy",
           :src
           "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"}]))

(defn include-bootstrap-css
  []
  (include-css "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"))


(defn common [& body]
  (html5
   [:head
    [:title "TODO List"]
    (include-bootstrap-css)]

   [:body body (include-bootstrap-scripts)]))
