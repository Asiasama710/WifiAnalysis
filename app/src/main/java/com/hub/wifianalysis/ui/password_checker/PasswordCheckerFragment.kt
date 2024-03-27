package com.hub.wifianalysis.ui.password_checker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hub.wifianalysis.R
import com.hub.wifianalysis.databinding.FragmentPasswordCheckerBinding

/**
 * PasswordCheckerFragment is a class that extends Fragment and is used to display the password checker screen.
 * It uses the layout defined in R.layout.fragment_password_checker for the fragment.
 *
 * @property binding The binding object that represents the view of the fragment.
 */
class PasswordCheckerFragment : Fragment() {

    private lateinit var binding: FragmentPasswordCheckerBinding

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordCheckerBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
     *
     * @param view The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.passwordEditText.addTextChangedListener(passwordWatcher)

        binding.checkPasswordButton.setOnClickListener {
            checkPasswordStrength()
        }
    }

    /**
     * TextWatcher for the password EditText. It clears the error and strength text after the password has been changed.
     */
    private val passwordWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            binding.passwordStrengthTextView.text = ""
            binding.passwordTextInputLayout.error = null
        }
    }

    /**
     * Checks the strength of the password entered by the user and updates the UI accordingly.
     * The password is considered strong if it matches the given regex pattern.
     */
    private fun checkPasswordStrength() {
        val password = binding.passwordEditText.text.toString()

        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"

        if (password.isEmpty()) {
            binding.passwordTextInputLayout.error = "Password cannot be empty"
            return
        }

        if (password.matches(Regex(passwordRegex))) {
            binding.passwordStrengthTextView.text = "Password is strong"
            binding.passwordStrengthTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        } else {
            binding.passwordTextInputLayout.error = "Password must contain at least one digit, one lowercase, one uppercase letter, one special character, and no whitespaces and at least long 8 characters"
            binding.passwordStrengthTextView.text = "Password is weak"
            binding.passwordStrengthTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.md_theme_error
                )
            )
        }
    }
}