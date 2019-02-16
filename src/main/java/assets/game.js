var isSetup = true;
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
}

function markHits(board, elementId, surrenderText) {
    console.log("Call the markhit function!");
    board.attacks.forEach((attack) => {
        let className;
        if (attack.result === "MISS")
            className = "miss";
        else if (attack.result === "HIT")
            className = "hit";
        else if (attack.result === "SUNK")
            className = "hit";
        else if (attack.result === "SURRENDER")
            alert(surrenderText);
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
    });
}

function redrawGrid() {
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
    //markHits(game.opponentsBoard, "opponent", "You won the game");
    //markHits(game.playersBoard, "player", "You lost the game");
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
            /*
            if(shipType == "MINESWEEPER" && type1 == false){
                    type1 = true;
                    pass = true;
            }
            else if (shipType == "DESTROYER" && type2 == false){
                    type2 = true;
                    pass = true;
            }
            else if(shipType == "BATTLESHIP" && type3 == false){
                    type3 = true;
                    pass = true;
            }
            */
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
    else {
        console.log("In ");
        sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
            game = data;
            redrawGrid();
        })
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
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
