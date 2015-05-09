;;;
;;; A highly simplified problem for Indiana Jones and the Raiders of the Lost Ark
;;; Created by Stephen G. Ware
;;;
(define (problem get-ark)
  (:domain indiana-jones-ark)
  (:objects indiana nazis army shaka - character
            usa tanis africa - place
            gun sword - weapon)
  (:init (intends shaka (has indiana sword))
		 (intends shaka (alive shaka))
         (intends indiana (alive indiana))
         (intends indiana (has army ark))
		 (intends indiana (has indiana sword))
		 (intends army (alive army))
         (intends army (has army ark))
		 (intends nazis (alive nazis))
         (intends nazis (open ark))
		 (buried ark tanis)
         (alive indiana)
         (at indiana usa)
         (knows-location indiana ark tanis)
		 (has shaka sword)
         (alive army)
         (at army usa)
		 (alive shaka)
		 (at shaka africa)
         (alive nazis)
         (at nazis tanis)
         (has nazis gun))
	
(:goal (not (alive indiana))))

;;  (:goal (and (at army usa)
;;              (has army ark)
;;              (not (alive nazis))
;;			  (has indiana sword))))