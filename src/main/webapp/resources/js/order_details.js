$(document).ready(function() {

    $('#orderDetailsForm').bootstrapValidator({
        message: 'This value is not valid',
        live: 'enabled',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            contactName: {
                message: 'The name is not valid',
                validators: {
                    notEmpty: {
                        message: 'The name is required and can\'t be empty'
                    },
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: 'The name must be more than 2 and less than 30 characters long'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z ]+$/,
                        message: 'The name can only consist of alphabetical and space'
                    }
                }
            },
            contactEmail: {
                validators: {
                    notEmpty: {
                        message: 'The email address is required and can\'t be empty'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            contactPhone: {
                validators: {
                    notEmpty: {
                        message: 'The phone is required and can\'t be empty'
                    },
                    phone: {
                        message: 'The input is not a valid phone number',
                        country: 'US'
                    }
                }
            }
        }
    });

});
