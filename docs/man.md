Name  
create - create an entity in the game  
Synopsis  
create room <u>name</u> [-c <u>capacity</u>]  
create (professor | student) <u>name</u> <u>spawn-room</u> [-i <u>inventory-size</u>]  
create janitor <u>name</u> <u>spawn-room</u>  
create door <u>name</u> <u>room-1</u> <u>room-2</u> [-o | --one-way]  
create (slidrule | gas-mask | tvsz | transistor | beer | camambert | cocktail | sponge) <u>name</u> (<u>actor</u> | <u>room</u>) [-u <u>uses-left</u>] [-f]  

Name  
effect - add effect to an actor or room  
Synopsis  
effect (<u>gas</u> | <u>wet</u> | <u>sticky</u>) <u>room</u> [-t <u>time-left</u>]  
effect stun (<u>student</u> | <u>janitor</u>) [-t <u>time-left</u>]  

Name  
move - move an actor through a door  
Synopsis  
move (<u>professor</u> | <u>student</u> | <u>janitor</u>) <u>door</u>  

Name  
use - use an item with a professor or student  
Synopsis  
use (<u>professor</u> | <u>student</u>) <u>item</u>  

Name  
drop - drop an item from the inventory of a professor or student  
Synopsis  
drop (<u>professor</u> | <u>student</u>) <u>item</u>  

Name  
pickup - pick up an item from a room with a professor or student  
Synopsis  
pickup (<u>professor</u> | <u>student</u>) <u>item</u>  

Name  
merge - merge a room into an other  
Synopsis  
merge <u>room-1</u> <u>room-2</u>  

Name  
split - split a room into two separate ones  
Synopsis  
split <u>room</u> <u>new-room-name</u>  

Name  
tick - make the in-game time progress on a room or actor  
Synopsis  
tick (<u>room</u> | <u>actor</u>)  

Name  
hide - hide a door  
Synopsis  
hide <u>door</u> [-t <u>time</u>]  

Name  
status - get information about the current status of the game  
Synopsis  
status <u>room</u> (effects | doors | actors | capacity | inventory | sticky)  
status <u>door</u> (invisible | oneway | rooms)  
status <u>item</u> (uses-left | inventory | fake | location)  
status <u>transistor</u> pair  
status <u>actor</u> (alive | location | inventory | defense | stunned)  
