(define (problem door)
  (:domain door)
  (:requirements :bardiche)
  (:objects knight guard - character
            village gate_outside gate_inside - room
            gate - door
            rope - rope)
  (:protagonist knight)
  
  (:init    
            ;; geography
            (adjacent village gate_outside)
            (climbable gate_outside gate_inside)
            (in gate gate_outside)
            (in gate gate_inside)
            (between gate gate_outside gate_inside)
            (shop village rope)
            
            ;;knight
            (at knight village)
            (rich knight)
            (intends knight (at knight gate_inside))
            (intends knight (barricaded gate))
            (intends knight (dead guard))
            (intends knight (not (dead knight)))
            
            ;;guard
            (at guard gate_outside)
            (can_open guard gate)
            (intends guard (not (at knight gate_inside)))
            (intends guard (dead knight))
            (intends guard (not (dead guard)))
            
            ;;mercenary
;;            (at mercenary village)
;;            (intends mercenary (rich mercenary))
;;            (intends mercenary (not (dead mercenary)))
  )
  
  (:bardichegoal
    (good
        (and (dead guard)
             (not (bribed guard))
             (at knight gate_inside))
        (and (not (dead guard))
             (bribed guard)
             (at knight gate_inside))
        (and (not (open gate))
             (at knight gate_inside)
             (not (at guard gate_inside))
             (barricaded gate))
    )
    (bad
        (dead knight)
        (and (not (dead guard))
             (not (bribed guard))
             (at knight gate_inside)
             (at guard gate_inside))
    )
  )
)
            