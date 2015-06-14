;;;
;;; A highly simplified problem for Indiana Jones and the Raiders of the Lost Ark
;;; Created by Stephen G. Ware
;;; Adapted by Geerten Vink for use with the Bardiche system
;;;
(define (problem get-ark)
  (:domain indiana-jones-ark)
  (:objects indiana nazis - character
            army nazi_army - army
            usa tanis germany - place
            gun sword - weapon)
  (:init (at indiana usa)
         (knows-location indiana ark tanis)
         (loves indiana marion)
         (intends indiana (not (dead indiana)))
         (intends indiana (has army ark))
		 
		 (buried ark tanis)
         
         (at nazis tanis)
         (has nazis gun)
         (intends nazis (not (dead nazis)))
         (intends nazis (open ark))
         
         (at belloq tanis)
         (loves belloq marion)
         (intends belloq (open ark))
         
         (at army usa)
         (intends army (not (defeated army)))
         (intends army (occupied army germany))
         (intends army (defeated nazi_army))
         (intends army (has army ark))
         
         (at nazi_army germany)
         (intends nazi_army (not (defeated nazi_army)))
         (intends nazi_army (defeated army))
         (intends nazi_army (occupied nazi_army usa))
  )
	
(:bardichegoal
    (good
        
    )
    (bad
        (win nazi_army)
        (dead indiana)
        (married marion belloq)
    )
)