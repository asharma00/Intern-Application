//document.addEventListener('DOMContentLoaded', function(e) {
//    FormValidation.formValidation(
//        document.getElementById('form'),
//        {
//            fields: {
//                fullName: {
//                    validators: {
//                        notEmpty: {
//                            message: 'The name is required'
//                        },
//                        stringLength: {
//                            min: 4,
//                            max: 50,
//                            message: 'The name must be more than 6 and less than 50 characters long'
//                        },
//                        regexp: {
//                            regexp: /^[a-z ,.'-]+$/i,
//                            message: 'Invalid characters in name'
//                        }
//                    }
//                },
//                age: {
//                    validators: {
//                        notEmpty: {
//                            message: 'The age is required'
//                        },
//                        numeric: {
//                            message: 'The age must be a number'
//                        }
//                    }
//                },
//                email: {
//                    validators: {
//                        notEmpty: {
//                            message: 'The size is required'
//                        },
//                        regexp: {
//                        	regexp: /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i,
//                        	message: 'The email is not valid'
//                        }
//                    }
//                },
//                parentName: {
//                    validators: {
//                        notEmpty: {
//                            message: 'The name is required'
//                        },
//                        stringLength: {
//                            min: 4,
//                            max: 50,
//                            message: 'The name must be more than 6 and less than 50 characters long'
//                        },
//                        regexp: {
//                            regexp: /^[a-z ,.'-]+$/i,
//                            message: 'Invalid characters in name'
//                        }
//                    }
//                },
//                address: {
//                	validators: {
//                		notEmpty: {
//                			message: 'The address is required'
//                		},
//                		stringLength: {
//                			max: 250,
//                			message: 'The address should be less than 250 characters long'
//                		}
//                	}
//                },
//                schoolName: {
//                	validators: {
//                		notEmpty: {
//                			message: 'The School Name is required'
//                		},
//                		stringLength: {
//                			max: 250,
//                			message: 'The School Name should be less than 250 characters long'
//                		}
//                	}
//                },
//                contact: {
//                	validators: {
//                		notEmpty: {
//                    		message: 'The contact is required'
//                    	},
//                    	regexp: {
//                    		regexp:  /^\d{10}$/,
//                    		message: 'Invalid characters in the contact information'
//                    	}
//                	}
//                }
//                
//            },
//            plugins: {
//                trigger: new FormValidation.plugins.Trigger(),
//                bootstrap: new FormValidation.plugins.Bootstrap(),
//                submitButton: new FormValidation.plugins.SubmitButton(),
//                icon: new FormValidation.plugins.Icon({
//                    valid: 'fa fa-check',
//                    invalid: 'fa fa-times',
//                    validating: 'fa fa-refresh'
//                }),
//            },
//        }
//    );
//});

$(document).ready(function () {
  var navListItems = $('div.setup-panel div a'),
          allWells = $('.setup-content')

  allWells.hide();


  $(function() {
  	$("#form").validate({
  		rules: {
  			fullName: {
  				required: true,
  				maxlength: 20
  			},
  			
  			email: {
  				required: true,
  				email: true,
  				maxlength: 40
  			},
  			parentName: {
  				required: true,
  				maxlength: 40
  			},
  			address: {
  				required: true,
  				maxlength: 250
  			},
  			schoolName: {
  				required: true,
  				maxlength: 40
  			},
  			contact: {
  				required: true,
  				number: true,
  				maxlength: 10,
  				minlength: 10,
  				min:0
  			},
  			schoolId: {
  				required: true
  			},
  			score: {
  				required: true,
  				number:true,
  				maxlength:3,
  				minlength:0,
  				max:100,
  				min:0
  			}
  		},
  		message: {
  			fullName: {
  				maxlength: 'Your name cannot exceed 40 characters'
  			},
  			
  			email: {
  				email: 'Please enter a valid email',
  				maxlength: 'Your email cannot exceed 40 characters'
  			},
  			score: { 
  				number: 'you can enter digits only',
  				maxlength:'your score must be between 0-100'
  			},
  			parentName: {
  				maxlength: 'Parent/Guardian name cannot exceed 40 characters' ,
  				minlength:'Parent/Guardian name cannot be less than 10 characters'
  			},
  			address: {
  				maxlength: 'Your address cannot exceed 250 characters'
  			},
  			schoolName: {
  				maxlength: 'Your School name must not exceed 40 characters'
  			},
  			contact: {
  				number: 'You can enter digits only',
  				maxlength: 'Your contact must be 10 digits long'
  			}
  		}
  	})
  });
  
  
  
  
  navListItems.click(function (e) {
      e.preventDefault();
      var $target = $($(this).attr('href')),
              $item = $(this);

      if (!$item.hasClass('disabled')) {
          navListItems.removeClass('btn-primary').addClass('btn-default');
          $item.addClass('btn-primary');
          allWells.hide();
          $target.show();
          $target.find('input:eq(0)').focus();
      }
  });
  
  $('.firstNextButton').click(function() {
  var ar=$('#area_drop').val();
      var curStep = $(this).closest(".setup-content"),
      curInputs = curStep.find("input[type='text'],input[type='url']")

      $(".form-group").removeClass("has-error");
	  
      if ($("#form").valid()) {
	    $.ajax({
	  
	    	type: 'POST',
	        url: "/internapp/api/v4/applications",
	        data: { 
	        	"fullName" : $('#fullName').val(),
	        	"age" : $('#age').val(),
	        	"email": $('#email123').val(),
	        	"parentName" : $('#parentName').val(),
	        	"address": $('#address').val(),
	        	"schoolName" : $('#schoolName').val(),	
	        	"contact": $('#contact').val(),
	    		"schoolId": $('#schoolId').val(),
	    		"score": $('#score').val(),
	    		"status": "Started",
	    		"area_of_interest":String(ar)
	    	},
	    	success: function(data) {
	    	
	    		if(data!=null) {
	 //alert("data not null");
	 
	    		  $("#userId").val(data);
	    		  form = new FormData();
	    		  file = document.getElementById("photo").files[0];
	    		  form.append('file', file);  //to display the file name after it is selected
	    		  form.append('id', $('#userId').val()); //formData.append(name, value) – add a form field with the given name and value
	    		  form.append('type', 1);
	    		  form.append('email', $('#email123').val())
	    		  $.ajax({
	    		    	type: 'POST',
	    		        url: "/internapp/api/v4/upload",
	    		        data: form,
	    		        processData: false,
	    		        contentType: false,
	    		        success: function(data) {
	    		    		  $(".firstButton").addClass('disabled');
	    		    	      $(".secondButton").removeClass("disabled").click();
	    		    	}
	    		  });
	    		}
	    		else {
	    			alert(data);
	    		}
	    	}
      });
  	}
   });
  
  $('.secondNextButton').click(function() {
	 var emailId= $('#email123').val();
	 
	  var userId = $('#userId').val();
	  var form = new FormData();
	  var file = document.getElementById("resume").files[0];
	  form.append('file', file);
	  form.append('id', userId);
	  form.append('type', 0);
	  form.append('email', emailId);
	  $.ajax({
	    	type: 'POST',
	    	url: "/internapp/api/v4/upload",
	        data: form,
	        processData: false,
	        contentType: false
	    	}).then(function(data) {
	    	});
	  form = new FormData();
	  file = document.getElementById("certificate").files[0];
	  form.append('file', file);
	  form.append('id', userId);
	  form.append('type', 2);
	  form.append('email', emailId);
	  $.ajax({
	    	type: 'POST',
	        url: "/internapp/api/v4/upload",
	        data: form,
	        processData: false,
	        contentType: false
	    	}).then(function(data) {
	    	});
	  form = new FormData();
	  file = document.getElementById("idproof").files[0];
	  form.append('file', file);
	  form.append('id', userId);
	  form.append('type', 3);
	  form.append('email', emailId);
	  $.ajax({
	    	type: 'POST',
	    	url: "/internapp/api/v4/upload",
	        data: form,
	        processData: false,
	        contentType: false
	    	}).then(function(data) {
	    	});
	  $.ajax({
	    	type: 'POST',
	        url: "/internapp/api/v4/applications/Initiated",
	        data: { 
	        	"userId" : userId,
	    		"status": "Initiated"
	    	},
	    	success: function(data) {
	    		  console.log(data);
	    	      $(".secondButton").addClass('disabled');
	    	      $(".thirdButton").removeClass('disabled').click();
	    	}
    });
   });
  
  $('div.setup-panel div a.btn-primary').trigger('click');
});

function showSpinner() {
	$('body').append("<div class='d-flex align-items-center justify-content-center spinner'><div class='spinner-border' role='status'><span class='sr-only'>Loading...</span></div></div>");
}

function hideSpinner() {
	$('.spinner').remove();
}
