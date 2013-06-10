(ns galgje.model
	(:require [noir.session :as session])
)

(def init-state {
	:total-guesses 1
	:word "winner"
})

(def chars-guessed (atom #{}))

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
	(some #{char-guessed} (get-word))
)

(defn guessed-character? [char-guessed]
	(@chars-guessed char-guessed)
)

(defn get-remaining-characters []
	(->>
		(for [c (get-word)]
			(if (guessed-character? c)
				c
				\_
			)
		)
		(apply str)
	)
)

(defn get-word-vector []
	(clojure.string/split (get-word) #"")
)

(defn new-state [old-state]
	(update-in old-state [:total-guesses] inc)
)

(defn add-char-guessed [char-guessed]
	(when-not (@chars-guessed char-guessed)
		(swap! chars-guessed conj char-guessed)
	)
)

(defn draw-hangman! []
	(session/swap!
		(fn [session-map]
			(update-in session-map [:game-state] new-state)
		)
	)
)

(defn winner? []
	(not(some #{\_} (get-remaining-characters)))
)