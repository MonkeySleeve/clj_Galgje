(ns galgje.view
  (:use hiccup.form
        [hiccup.def :only [defhtml]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [html5 include-css]])
  (:require [galgje.model :as model]))

(defhtml layout [& content]
  (html5
   [:head
    [:title "Welcome to galgje-luminus"]
    (include-css "/css/galgje.css")]
   [:body [:div#wrapper content]]))

(defn input-field []
  [:input {:name (str "guess")
           :maxlength 1
           :type (str "text")}])

(defn hangman-image []
  [:img {
    :src (str "hangman" (model/get-turn) ".png")
    :alt ("Hangman")
  }]
)

(defn cell-html [rownum colnum cell with-submit?]
  [:td
   [:input {:name (str "b" rownum colnum)
            :value (str cell)
            :type (if with-submit?
                    "submit"
                    "button")}]])

(defn row-html [rownum row with-submit?]
  [:tr (map-indexed (fn [colnum cell]
                      (cell-html rownum colnum cell with-submit?))
                    row)])

(defn board-html [board with-submit?]
  (form-to [:post "/"]
           [:table
            (map-indexed (fn [rownum row]
                           (row-html rownum row with-submit?))
                         board)]))

(defn play-screen []
  (layout
    [:div
     (input-field)
       [:p "Turn " (model/get-turn) ", choose a letter!"]
       (board-html (model/get-board) true)]))

(defn winner-screen [winner]
  (layout
    [:div
   [:p "The winner is: " winner]
   (board-html (model/get-board) false)
   (link-to "/" "Reset")]))

(defn draw-screen []
  (layout
    [:div
     [:p "It's a draw!"]
     (board-html (model/get-board) false)
     (link-to "/" "Reset")]))