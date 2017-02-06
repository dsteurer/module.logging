(ns duct.module.logging
  (:require [integrant.core :as ig]
            [duct.core :refer [assoc-in-default]]
            [duct.logger.timbre :as timbre]
            [duct.middleware.timbre :as mw]
            [duct.module.web :as web]))

(defmethod ig/init-key :duct.module/logging [_ _]
  (fn [config]
    (-> config
        (assoc-in-default [:duct.logger/timbre :level] :info)
        (assoc-in-default [:duct.logger/timbre :appenders :println] (ig/ref ::timbre/println))
        (assoc-in-default [::timbre/println :stream] :auto)
        (web/add-middleware ::mw/binding         (ig/ref :duct.logger/timbre))
        (web/add-middleware ::mw/request-logging (ig/ref :duct.logger/timbre)))))