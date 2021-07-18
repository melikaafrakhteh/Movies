package com.afrakhteh.movies.ui.fragment.home.seeAll.newSeeAll;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.afrakhteh.movies.R;

public class NewSeeAllFragmentDirections {
  private NewSeeAllFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNewSeeAllFragmentToHomeFragment() {
    return new ActionOnlyNavDirections(R.id.action_newSeeAllFragment_to_homeFragment);
  }
}
