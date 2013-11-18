           
/*
* Workaround: Primefaces orderList component doesn't really do anything usefull.
**/
function updateSelectedTracks(){
    var value = "";
    jQuery(".selectedTracks .ui-orderlist-item").each(function(index, element){
        var id = jQuery(this).data("item-value");
        if(id != undefined){
            value+=id+",";
        }
    });
    if(value.length != 0){
        value = value.substr(0, value.length-1);
    }
    jQuery("input[name='j_idt6:selectedTracks']").val(value);
}