(define (domain dungeon)
  (:requirements :adl :domain-axioms :intentionality :bardiche)
  (:types voorwerp personage kamer)
  (:predicates (heeft ?personage - personage ?voorwerp - voorwerp)
               (in ?personage - personage ?kamer - kamer)
               (in_vw ?voorwerp - voorwerp ?kamer - kamer)
               (naast ?kamer - kamer ?buur - kamer)
               (dood ?personage - personage)
               (wapen ?voorwerp - voorwerp)
               (geheim ?kamer - kamer)
               (gewapend ?personage - personage)
               (gewond ?personage - personage)
               (samen ?personage - personage ?erbij - personage)
               (bij ?personage - personage ?voorwerp - voorwerp)
               (samen_en_levend ?personage - personage ?erbij - personage))
  
  (:action loop
    :parameters     (?personage - personage ?van - kamer ?naar - kamer)
    :precondition   (and (in ?personage ?van)
                         (naast ?van ?naar)
                         (not (= ?van ?naar))
                         (not (dood ?personage))
                         (not (geheim ?naar)))
    :effect         (and (not (in ?personage ?van))
                         (in ?personage ?naar))
    :agents         (?personage))
    
  (:action pak_op
    :parameters     (?personage - personage ?voorwerp - voorwerp ?kamer - kamer)
    :precondition   (and (in ?personage ?kamer)
                         (in_vw ?voorwerp ?kamer))
    :effect         (and (heeft ?personage ?voorwerp)
                         (not (in_vw ?voorwerp ?kamer)))
    :agents         (?personage))
    
  (:action laat_vallen
    :parameters     (?personage - personage ?voorwerp - voorwerp ?kamer - kamer)
    :precondition   (and (in ?personage ?kamer)
                         (heeft ?personage ?voorwerp))
    :effect         (and (not (heeft ?personage ?voorwerp))
                         (in_vw ?voorwerp ?kamer))
    :agents         (?personage))
    
  (:action sla
    :parameters     (?personage - personage ?doel - personage)
    :precondition   (and (samen_en_levend ?personage ?doel)
                         (gewapend ?personage))
    :effect         (dood ?doel)
    :agents         (?personage))
    
  (:action vind
    :parameters     (?personage - personage ?kamer - kamer ?gevonden - kamer)
    :precondition   (and (in ?personage ?kamer)
                         (not (dood ?personage))
                         (naast ?kamer ?gevonden)
                         (geheim ?gevonden))
    :effect         (not (geheim ?gevonden))
    :agents         (?personage))
    
;; AXIOMS
    
  (:axiom
    :vars           (?kamer - kamer ?buur - kamer)
    :context        (naast ?kamer ?buur)
    :implies        (naast ?buur ?kamer))
    
  (:axiom
    :vars           (?personage - personage ?voorwerp - voorwerp)
    :context        (and (heeft ?personage ?voorwerp)
                         (wapen ?voorwerp))
    :implies        (gewapend ?personage))
    
  (:axiom
    :vars           (?personage - personage)
    :context        (forall (?wapen - voorwerp)
                            (not (heeft ?personage ?wapen)))
    :implies        (not (gewapend ?personage)))
    
  (:axiom
    :vars           (?personage - personage ?erbij - personage ?kamer - kamer)
    :context        (and (in ?personage ?kamer)
                         (in ?erbij ?kamer)
                         (not (= ?personage ?erbij)))
    :implies        (samen ?personage ?erbij))
    
  (:axiom
    :vars           (?personage - personage ?erbij - personage ?kamer - kamer)
    :context        (and (in ?personage ?kamer)
                         (not (in ?erbij ?kamer)))
    :implies        (not (samen ?personage ?erbij)))
    
  (:axiom
    :vars           (?personage - personage ?erbij - personage ?kamer - kamer)
    :context        (and (in ?personage ?kamer)
                         (in ?erbij ?kamer)
                         (not (= ?personage ?erbij))
                         (not (dood ?personage))
                         (not (dood ?erbij)))
    :implies        (samen_en_levend ?personage ?erbij))
    
  (:axiom
    :vars           (?personage - personage)
    :context        (dood ?personage)
    :implies        (forall (?erbij - personage)
                            (not (samen_en_levend ?personage ?erbij))))
)