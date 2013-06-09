(ns galgje.controller
	(:use compojure.core)
	(:require [compojure.core :as compojure]
		[galgje.view :as view]
		[galgje.model :as model]
	)
)

(defn start-page []
	(model/reset-game!)
	(view/play-screen)
)

(defn letter-submit [input-params]
	; 
	; (let [button-id (name (first (keys input-params)))
	;       rownr (Integer/parseInt (str (second button-id)))
	;       colnr (Integer/parseInt (str (nth button-id 2)))]
 
 (if-not(model/is-char-in-word? (get input-params :guess))
   (model/draw-hangman!)(println "hang that man!")
   )
 (model/add-char-guessed (get input-params :guess))
 (view/play-screen)
		; (if-let [winner (model/winner?)]
		;   (view/winner-screen winner)
		;   (if (model/full-board?)
		;     (view/draw-screen)
		;     (view/play-screen)
)

(defroutes galgje-routes
	(GET "/" [] (start-page))
	(POST "/" {input-params :params}
		(letter-submit input-params)
	)
)