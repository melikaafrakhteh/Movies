package com.afrakhteh.movies.ui.fragment.splash;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.afrakhteh.movies.R;

public class SplashScreenFragmentDirections {
  private SplashScreenFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionSplashFragmentToLoginFragment() {
    return new ActionOnlyNavDirections(R.id.action_splashFragment_to_loginFragment);
  }

  @NonNull
  public static NavDirections actionSplashFragmentToHomeFragment() {
    return new ActionOnlyNavDirections(R.id.action_splashFragment_to_homeFragment);
  }
}
