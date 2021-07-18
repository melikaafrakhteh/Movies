package com.afrakhteh.movies.ui.fragment.home;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.afrakhteh.movies.R;

public class HomeFragmentDirections {
  private HomeFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionHomeFragmentToNewSeeAllFragment() {
    return new ActionOnlyNavDirections(R.id.action_homeFragment_to_newSeeAllFragment);
  }

  @NonNull
  public static NavDirections actionHomeFragmentToPopularSeeAllFragment() {
    return new ActionOnlyNavDirections(R.id.action_homeFragment_to_popularSeeAllFragment);
  }
}
