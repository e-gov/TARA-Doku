
function init() {

  // Hide all submenus
  // $('.alammenyy').hide();

  // Bind toggling handler to submenu switches
  $('.lyliti').click(function() {
    var i = $(this).attr('id');
    $('#alammenyy' + i).toggle();
  });
}