var isSetup = true;
var isSonar = false;
var placedShips = 0;
var game;
var shipType;
var vertical;
var type1 = false; //Minesweeper
var type2 = false; //Destroyer
var type3 = false; //BattleShip
var pass = false;
function makeGrid(table, isPlayer) {
    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        for (j=0; j<10; j++) {
            let column = document.createElement('td');
            column.addEventListener("click", cellClick);
            row.appendChild(column);
        }
        table.appendChild(row);
    }
    console.log("Adding the caption!");
    let title = document.createElement('caption');
    let content = document.createElement('font');
    content.style.fontSize="30px";
    if(isPlayer)
    {
        let context = document.createTextNode("Player");
        content.appendChild(context);
    }
    else
    {
        let context = document.createTextNode("Opponent");
        content.appendChild(context);
    }
    title.appendChild(content);
    table.appendChild(title);
}

function markHits(board, elementId, surrenderText) {
    console.log("Call the markhit function!");

    if(elementId == "player")
    {
        //Draw the sonar attack
        board.sonar_pulse.forEach((sonar) => {
            let length = 1;
            let side = 0;
            //Find the coordinate of the sonar pulse
            let coord_x = sonar.row - 1;
            let coord_y = sonar.column.charCodeAt(0) - 'A'.charCodeAt(0);
            let target_x, target_y;
            for(i = 0; i < 5; i++)
            {
                for(j = 0; j < length; j++)
                {
                    target_x = coord_x - side + j;
                    target_y = coord_y - 2 + i;
                    //Draw all the sonar grid
                    if(!(target_x < 0 || target_x > 9 || target_y < 0 || target_y > 9))
                    {
                        document.getElementById("opponent").rows[target_x].cells[target_y].classList.add("sonar");

                        //To check if the target grid is a ship
                        game.opponentsBoard.ships.forEach((tship) => {
                            tship.occupiedSquares.forEach((tsquare) => {
                                if(tsquare.row - 1 == target_x && (tsquare.column.charCodeAt(0) - 'A'.charCodeAt(0)) == target_y)
                                {
                                    console.log("Ship found!");
                                    document.getElementById("opponent").rows[target_x].cells[target_y].classList.add("occupied");
                                }
                            });
                        });
                    }
                }
                if(i < 2){
                    length += 2;
                    side++;
                }
                else{
                    length -= 2;
                    side--;
                }
            }
        });
    }

    board.attacks.forEach((attack) => {
        let classname;
        if (attack.result === "MISS")
            classname = "miss";
        else if (attack.result === "CAPTAIN")
            classname = "miss";
        else if (attack.result === "HIT")
            classname = "hit";
        else if (attack.result === "SUNK")
            classname = "hit";
        else if (attack.result === "SURRENDER")
            alert(surrenderText);
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(classname);
    });
}

function redrawGrid() {
    //Reset the sonar flag
    isSonar = false;

    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    markHits(game.playersBoard, "player", "You fucked up!");
    markHits(game.opponentsBoard, "opponent", "No");
    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => {
        console.log(ship.ship_type);
    })

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        //Testing feature:
        console.log(square.row-1, square.column.charCodeAt(0));
        //
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));
    game.playersBoard.ships.forEach((ship) => ship.captainSquares.forEach(square => {

        console.log(square.row-1, square.column.charCodeAt(0));
    document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0)-'A'.charCodeAt(0)].classList.remove("occupied");
    document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0)-'A'.charCodeAt(0)].classList.add("captain");
}));
    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
}




var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    var parentTag = this.parentNode.parentNode.id;
    //Test
    console.log(parentTag);
    //

    if (isSetup) {
        if (parentTag == "opponent")
        {
            var para = document.createElement("P");
            var t = document.createTextNode("Captain it's not our territory!");
            para.appendChild(t);
            document.getElementById("inf_table").appendChild(para);
        }
        else
        {
            pass = true;
            if(pass == true){
                sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical}, function(data) {
                    var para = document.createElement("P");
                    var t = document.createTextNode("Succuessfully place your ship");
                    para.appendChild(t);
                    document.getElementById("inf_table").appendChild(para);
                    game = data;
                    redrawGrid();
                    placedShips++;
                    if (placedShips == 3)
                    {
                        isSetup = false;
                        registerCellListener((e) => {});
                    }
                });
            }
            else {
                var para = document.createElement("P");
                var t = document.createTextNode("You have already placed this type of ship");
                para.appendChild(t);
                document.getElementById("inf_table").appendChild(para);
           }
        }
    }
    else if (isSonar)
    {
        sendXhr("POST", "/sonar", {game: game, x: row, y: col}, function(data) {
            console.log("Sonar result received!");
            console.log("Sonar data:")
            console.log(data);
            game = data;
            redrawGrid();
        })
    }
    else {

        if (parentTag == "player"){
        
            var para = document.createElement("P");
            var t = document.createTextNode("You cant shot your own land");
            para.appendChild(t);
            document.getElementById("inf_table").appendChild(para);
        }
        else{
            sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
                console.log("Attack result received!");
                game = data;
                redrawGrid();
            })
        }
      
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            //Reset the sonar flag
            isSonar = false;
            //alert("Cannot complete the action");
            var para = document.createElement("P");
            var t = document.createTextNode("Oops! You either click on wrong place or you place ship out of board.");
            para.appendChild(t);
            document.getElementById("inf_table").appendChild(para);
            return;
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    console.log("Sending data:");
    console.log(JSON.stringify(data));
    req.send(JSON.stringify(data));
}

function place(size) {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        vertical = document.getElementById("is_vertical").checked;
        let table = document.getElementById("player");
        for (let i=0; i<size; i++) {
            let cell;
            if(vertical) {
                let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else {
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }
            cell.classList.toggle("placed");
        }
    }
}

function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    document.getElementById("place_sonar_pulse").addEventListener("click", function(e) {
        isSonar = true;
        registerCellListener(place(1));
    });
    document.getElementById("place_minesweeper").addEventListener("click", function(e) {
        shipType = "MINESWEEPER";
        registerCellListener(place(2));
    });
    document.getElementById("place_destroyer").addEventListener("click", function(e) {
        shipType = "DESTROYER";
        registerCellListener(place(3));
    });
    document.getElementById("place_battleship").addEventListener("click", function(e) {
        shipType = "BATTLESHIP";
        registerCellListener(place(4));
    });
    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });
};
