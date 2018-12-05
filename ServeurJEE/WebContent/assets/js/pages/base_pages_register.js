/*
 *  Document   : base_pages_register.js
 *  Author     : pixelcave
 *  Description: Custom JS code used in Register Page
 */

var BasePagesRegister = function() {
    // Init Register Form Validation, for more examples you can check out https://github.com/jzaefferer/jquery-validation
    var initValidationRegister = function(){
        jQuery('.js-validation-register').validate({
            errorClass: 'help-block text-right animated fadeInDown',
            errorElement: 'div',
            errorPlacement: function(error, e) {
                jQuery(e).parents('.form-group > div').append(error);
            },
            highlight: function(e) {
                jQuery(e).closest('.form-group').removeClass('has-error').addClass('has-error');
                jQuery(e).closest('.help-block').remove();
            },
            success: function(e) {
                jQuery(e).closest('.form-group').removeClass('has-error');
                jQuery(e).closest('.help-block').remove();
            },
            rules: {
                'register-username': {
                    required: true,
                    minlength: 3
                },
                'register-email': {
                    required: true,
                    email: true
                },
                'register-password': {
                    required: true,
                    minlength: 5
                },
                'register-password2': {
                    required: true,
                    equalTo: '#register-password'
                },
                'register-terms': {
                    required: true
                }
            },
            messages: {
                'register-username': {
                    required: 'Entrer un mail svp',
                    minlength: 'Votre mail doit contenir au minimum 3 caractères'
                },
                'register-email': 'Entrer une adresse mail valide svp',
                'register-password': {
                    required: 'Entrer un mot de passe svp',
                    minlength: 'Votre mot de passe doit contenir au minimum 5 caractères'
                },
                'register-password2': {
                    required: 'Entrer un mot de passe svp',
                    minlength: 'Votre mot de passe doit contenir au minimum 5 caractères',
                    equalTo: 'Entrer le meme mot de passe svp'
                },
                'register-terms': 'Il faut accepter les conditions'
            }
        });
    };

    return {
        init: function () {
            // Init Register Form Validation
            initValidationRegister();
        }
    };
}();

// Initialize when page loads
jQuery(function(){ BasePagesRegister.init(); });
