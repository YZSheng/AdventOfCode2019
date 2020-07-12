(ns advent-of-code.day-ten
  (:require
    [clojure.java.io :as io]))

(defn parse-asteroid-line
  "parse a line of asteroids"
  [asteroid-line y]
  (->> asteroid-line
       (map-indexed (fn [idx char] {:x idx :y y :char char}))
       (filter (fn [{char :char}] (= char \#)))))

(defn read-file [path]
  (with-open [rdr (io/reader path)]
    (doall (line-seq rdr))))

(defn parse-asteroid-map
  "parse a map of asteroids"
  [asteroid-map]
  (->> asteroid-map
       read-file
       (map-indexed (fn [idx asteroid-line] (parse-asteroid-line asteroid-line idx)))
       flatten))

(parse-asteroid-line "...#..#" 0)

(parse-asteroid-map "resources/day_ten_part_one_sample_one.txt")

(defn calculate-distance-gradient
  [{from-x :x from-y :y} {to-x :x to-y :y}]
  {:distance (+ (Math/abs (- to-x from-x)) (Math/abs (- to-y from-y)))
   :gradient (if (= to-x from-x)
               (if (> to-y from-y)
                 :pos-infinite
                 :neg-infinite)
               (if (= to-y from-y)
                 (if (< to-x from-x)
                   :pos-zero
                   :neg-zero)
                 (if (> to-y from-y)
                   (str "pos" (/ (- to-y from-y) (- to-x from-x)))
                   (str "neg" (/ (- to-y from-y) (- to-x from-x))))))})


(calculate-distance-gradient {:x 1, :y 0, :char \#} {:x 4, :y 0, :char \#})
(calculate-distance-gradient {:x 4, :y 0, :char \#} {:x 1, :y 0, :char \#})
(calculate-distance-gradient {:x 4, :y 1, :char \#} {:x 1, :y 4, :char \#})
(calculate-distance-gradient {:x 2, :y 2, :char \#} {:x 1, :y 0, :char \#})
(calculate-distance-gradient {:x 2, :y 2, :char \#} {:x 3, :y 4, :char \#})


(defn transform-map
  [asteroid-map]
  (let [points (parse-asteroid-map asteroid-map)]
    (for [from points to points :when (not= from to)]
      (let [result (calculate-distance-gradient from to)]
        {:from from :to to :distance (:distance result) :gradient (:gradient result)}))))

(defn solution-part-one
  [asteroid-map]
  (sort-by last (for [[key value] (group-by :from (transform-map asteroid-map))]
                  (let [value-by-gradient (group-by :gradient value)]
                    [key (count (keys value-by-gradient))]))))


(solution-part-one "resources/day_ten_part_one_sample_one.txt")

(transform-map "resources/day_ten_part_one_sample_one.txt")

(solution-part-one "resources/day_ten_part_one_sample_two.txt")
(solution-part-one "resources/day_ten_part_one.txt")




