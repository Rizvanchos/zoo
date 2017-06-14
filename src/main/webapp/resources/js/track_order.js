$(document).ready(function() {

    $('#trackOrderForm').bootstrapValidator({
        message: 'This value is not valid',
        live: 'enabled',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            inputTrackOrderId: {
                message: 'Order ID is not valid',
                validators: {
                    notEmpty: {
                        message: 'The Order ID is required and can\'t be empty'
                    },
                    uuid: {
                        message: 'The Order ID must be a valid GUID'
                    }
                }
            },
            inputTrackOrderEmail: {
                validators: {
                    notEmpty: {
                        message: 'The email address is required and can\'t be empty'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            }
        }
    });

});

