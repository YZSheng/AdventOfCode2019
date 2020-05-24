(ns advent-of-code.day-eight
  (:require [clojure.java.io :as io]))

(defn read-file [path]
  (with-open [rdr (io/reader path)]
    (doall (line-seq rdr))))

(defn find-layers
  [input w h]
  (->> (clojure.string/split input #"")
       (map read-string)
       (partition (* w h) (* w h) nil)))

(defn get-frequencies-of
  [pixels num]
  (get (frequencies pixels) num))


(defn get-zero-frequencies
  [pixels]
  (get-frequencies-of pixels 0))

(defn find-layers-with-fewest-zero-digits
  [input w h]
  (->> (find-layers input w h)
       (group-by get-zero-frequencies)
       (into (sorted-map))
       first
       last
       flatten))

(find-layers "123456789012" 3 2)

(defn find-checksum
  [pixels]
  (* (get-frequencies-of pixels 1) (get-frequencies-of pixels 2)))


(->> (find-layers-with-fewest-zero-digits "123456789012" 3 2)
     find-checksum)


(-> "resources/day_eight_part_one.txt"
    read-file
    first
    (find-layers-with-fewest-zero-digits 25 6)
    find-checksum)


(defn combine-two-layers
  [a b]
  (for [index (range (count a))
        :let [a-val (nth a index)
              b-val (nth b index)]]
    (if (= 2 a-val)
      b-val
      a-val)))

(defn compose-layers
  [input w h]
  (->> (find-layers input w h)
       (reduce combine-two-layers)))

(defn print-composed-layers
  [input w h]
  (let [rows (->> (compose-layers input w h)
                  (partition w))]
    (doseq [row rows]
      (println (map #(if (pos? %) % " ") row)))))


(print-composed-layers "0222112222120000" 2 2)


(-> "resources/day_eight_part_one.txt"
    read-file
    first
    (print-composed-layers 25 6))