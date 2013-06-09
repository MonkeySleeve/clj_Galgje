(ns galgje.model
	(:require [noir.session :as session])
)

(def init-state {
	:total-guesses 1
	:word "winner"
})

(def chars-guessed (atom ()))

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

(defn is-char-in-word? [char-guessed]
	(.contains (str get-word) char-guessed)
)

(defn guessed-character? [char-guessed]
	(.contains (get-guessed-characters) char-guessed)
)

(defn get-remaining-characters []
	(for [x (range (count (get-word)))]
		(if (guessed-character? (subs (get-word) x (+ x 1)))
			(str (subs (get-word) x (+ x 1)))
			(str "_")
		)
	)
)

(defn get-word-vector []
	(clojure.string/split get-word #"")
)

(defn new-state [old-state]
	old-state
	{
		:total-guesses (inc (:total-guesses old-state))
		:word (:word old-state)
	}
)

(defn add-char-guessed [char-guessed]
	(if-not (.contains @chars-guessed char-guessed)
		(swap! chars-guessed conj char-guessed)
	)
)

(defn draw-hangman! []
	(session/swap!
		(fn [session-map]
			(assoc session-map :game-state
				(new-state (:game-state session-map))
			)
		)
	)
)

; (defn winner? []
; 	(println(str (get-remaining-characters)))
; 	(println(some #(= \_ %) (str (get-remaining-characters))))
; )