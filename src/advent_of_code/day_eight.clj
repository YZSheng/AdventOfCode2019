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

