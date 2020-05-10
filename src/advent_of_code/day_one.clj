(ns advent-of-code.day-one
  (:require [clojure.java.io :as io]))

(defn read-file [path]
  (with-open [rdr (io/reader path)]
    (doall (line-seq rdr))))

(defn get-fuel
  "gets fuel by mass"
  [mass]
  (- (quot mass 3) 2))

(defn find-sum-of-fuels [mass-list]
  (->> mass-list
       (map read-string)
       (map get-fuel)
       (reduce +)))

(->> "resources/day_one_part_one.txt"
     read-file
     find-sum-of-fuels)


;; part two

(defn fuel-required [mass]
  (reduce + (rest (take-while pos? (iterate get-fuel mass)))))

(defn find-sum-of-fuels-of-fuel [mass-list]
  (->> mass-list
       (map read-string)
       (map fuel-required)
       (reduce +)))

;; 5106629

(->> "resources/day_one_part_one.txt"
     read-file
     find-sum-of-fuels-of-fuel)