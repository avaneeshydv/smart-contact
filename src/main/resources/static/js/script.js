console.log("This is javascript file")

//code for side bar and button for small screen
const toggleSidebar = () => {

    if ($('.sidebar').is(':visible')) {

        $('.sidebar').css("display", "none");
        $('.content').css("margin-left", "0%");

    } else {
        $('.sidebar').css("display", "block");
        $('.content').css("margin-left", "20%");
    }

}

function enable() {
    document.getElementById("mySelect").disabled = false;
}