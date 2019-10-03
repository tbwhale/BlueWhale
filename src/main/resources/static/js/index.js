/**
 * 
 */
function pageJump(status){
	if (status=='signUp') {
		$("#signInForm").hide();
		$("#signUpForm").show();
		$("#forgotForm").hide();
	} else if (status=='signIn') {
		$("#signInForm").show();
		$("#signUpForm").hide();
		$("#forgotForm").hide();
	} else if (status=='forgot') {
		$("#signInForm").hide();
		$("#signUpForm").hide();
		$("#forgotForm").show();
	}
}