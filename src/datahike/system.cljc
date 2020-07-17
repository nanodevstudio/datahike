(ns datahike.system
  (:require [clojure.string :as s]
            #?(:cljs os))
  (:import #?(:clj [java.lang System])))

(defn join-paths [a b]
  (let [a-trails (s/ends-with? a "/")
        b-leads (s/starts-with? b "/")]
    (cond
      (and a-trails b-leads) (str a (subs b 1))
      (or a-trails b-leads) (str a b)
      :else (str a "/" b))))

(defn join [& strs]
  (reduce join-paths strs))

(defn temp-dir [subpath]
  #?(:clj
     (case (System/getProperty "os.name")
       "Windows 10"  (join (System/getProperty "java.io.tmpdir") subpath)
       (join "/tmp" subpath))
     :cljs
     (join (os/tempdir) subpath)))
