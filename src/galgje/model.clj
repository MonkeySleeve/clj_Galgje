(ns galgje.model
	(:require [noir.session :as session]))

(def init-state {:total-guesses 1 :word "winner"})


(def chars-guessed (atom {}))

(defn get-guessed-characters []
	(apply str (interpose ", " @chars-guessed))
)

(defn reset-game! []
	(session/put! :game-state init-state)
)

(defn get-total-guesses []
	(:total-guesses (session/get :game-state))
)

(defn get-word []
	(:word (session/get :game-state))
)


(defn new-state [old-state]
  old-state
	{
		:total-guesses (inc (:total-guesses old-state))
		:word (str "timo" (:word old-state))
	}
)
(defn guess-char [c]
  (swap! chars-guessed assoc (c ) :guess)
)
(defn play! [c]
  (guess-char [c])
	(session/swap! (fn [session-map]
		(assoc session-map :game-state
			(new-state (:game-state session-map))
			)
		)
	)
)
