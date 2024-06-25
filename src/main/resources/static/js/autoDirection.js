var sortState = {};

function sortTable(columnIndex) {
    var table, tbody, rows, switching, i, x, y, shouldSwitch, dir;
    table = document.getElementById("myTable");
    tbody = table.querySelector("tbody");
    switching = true;
    dir = "asc";

    if (!sortState[columnIndex]) {
        sortState[columnIndex] = "asc";
    } else {
        sortState[columnIndex] = sortState[columnIndex] === "asc" ? "desc" : "asc";
        dir = sortState[columnIndex];
    }

    while (switching) {
        switching = false;
        rows = tbody.rows;
        for (i = 0; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("td")[columnIndex];
            y = rows[i + 1].getElementsByTagName("td")[columnIndex];
            if (dir === "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    shouldSwitch = true;
                    break;
                }
            } else if (dir === "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
}




