(ns advent-of-code.day-six
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(defn parse-input
  "parse one pair of orbit input"
  [input]
  (let [[inner object] (string/split input #"\)")]
    {:inner inner :object object :outer #{}}))

(parse-input "A)B")

(defn get-children
  [node nodes]
  (->> nodes
       (filter #(= (:inner %) (:object node)))
       (map :object)
       set))


(get-children {:inner "A" :object "B" :outer #{}} [{:inner "A" :object "B" :outer #{}} {:inner "B" :object "C" :outer #{}} {:inner "B" :object "D" :outer #{}}])


(def a {:name #{2}})

(update a :name clojure.set/union [1])


(defn map-get-children
  [nodes]
  (map #(update % :outer clojure.set/union (get-children % nodes)) nodes))


(defn form-chain
  [input]
  (->> input
       (map parse-input)
       map-get-children))


(form-chain ["A)B" "B)C" "B)E" "C)D"])

(defn count-in-chain
  [node chains]
  (let [parent (some #(when (= (:inner node) (:object %)) %) chains)]
    (if parent
      (inc (count-in-chain parent chains))
      1)))

(count-in-chain (parse-input "C)D") (form-chain ["A)B" "B)C" "B)E" "C)D"]))

(defn total-count
  [input]
  (let [chains (form-chain input)]
    (->> chains
         (map #(count-in-chain % chains))
         (reduce +))))

(total-count ["A)B" "B)C" "B)E" "C)D"])

(defn read-file [path]
  (with-open [rdr (io/reader path)]
    (doall (line-seq rdr))))

(def sample-input ["COM)B"
                   "B)C"
                   "C)D"
                   "D)E"
                   "E)F"
                   "B)G"
                   "G)H"
                   "D)I"
                   "E)J"
                   "J)K"
                   "K)L"])

(total-count (read-file "resources/day_six_part_one.txt"))