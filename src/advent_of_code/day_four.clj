(ns advent-of-code.day-four)

(defn two-adjacent-digits-same
  [num]
  (->> (clojure.string/split (str num) #"")
       (partition-by identity)
       (some #(> (count %) 1))))

(defn exactly-two-adjacent-digits-same
  [num]
  (->> (clojure.string/split (str num) #"")
       (partition-by identity)
       (some #(= (count %) 2))))

(two-adjacent-digits-same 1233)

(exactly-two-adjacent-digits-same 111122)

(defn never-decrease
  "seq never decrease"
  [input]
  (if (< (count input) 2)
    true
    (and (<= (first input) (nth input 1)) (never-decrease (rest input)))))

(never-decrease [1 2 3])
(never-decrease [1 2 2])
(never-decrease [1 2 0])

(defn never-decrease-ltr
  "number digits never decrease left to right"
  [num]
  (->> (clojure.string/split (str num) #"")
       (map read-string)
       never-decrease))

(never-decrease-ltr 12345)
(never-decrease-ltr 54321)
(never-decrease-ltr 11111)
(never-decrease-ltr 223450)

(count (->> (range 158126 624574)
            (filter two-adjacent-digits-same)
            (filter never-decrease-ltr)))

(count (->> (range 158126 624574)
            (filter exactly-two-adjacent-digits-same)
            (filter never-decrease-ltr)))
