function showProgress(data) {  
    if (data.status === "begin") {
        document.getElementById('loading').style.display = "block";
        document.getElementById('loading').style.margin = "auto";
    } else if (data.status === "success") {
        document.getElementById('loading').style.display = "none";
    }
}


