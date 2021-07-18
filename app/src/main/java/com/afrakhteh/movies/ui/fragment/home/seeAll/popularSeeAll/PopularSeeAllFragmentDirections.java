package com.afrakhteh.movies.ui.fragment.home.seeAll.popularSeeAll;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.afrakhteh.movies.R;

public class PopularSeeAllFragmentDirections {
  private PopularSeeAllFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionPopularSeeAllFragmentToHomeFragment() {
    return new ActionOnlyNavDirections(R.id.action_popularSeeAllFragment_to_homeFragment);
  }
}
