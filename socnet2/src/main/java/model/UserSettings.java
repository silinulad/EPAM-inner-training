package model;

public class UserSettings {

	private long	userId;
	private boolean	visiblePageOnlyFriends;
	private boolean	hideFriends;
	private boolean	hideGroups;
	private boolean	hideGifts;
	private String	showDate;
	private boolean	hideCountry;
	private boolean	hideCitiy;
	private boolean	hideSex;
	private boolean	hidePhone;

	public long getUserId() {
		return userId;
	}

	public boolean isVisiblePageOnlyFriends() {
		return visiblePageOnlyFriends;
	}

	public boolean isHideFriends() {
		return hideFriends;
	}

	public boolean isHideGroups() {
		return hideGroups;
	}

	public boolean isHideGifts() {
		return hideGifts;
	}

	public String getShowDate() {
		return showDate;
	}

	public boolean isHideCountry() {
		return hideCountry;
	}

	public boolean isHideCitiy() {
		return hideCitiy;
	}

	public boolean isHideSex() {
		return hideSex;
	}

	public boolean isHidePhone() {
		return hidePhone;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setVisiblePageOnlyFriends(boolean visiblePageOnlyFriends) {
		this.visiblePageOnlyFriends = visiblePageOnlyFriends;
	}

	public void setHideFriends(boolean hideFriends) {
		this.hideFriends = hideFriends;
	}

	public void setHideGroups(boolean hideGroups) {
		this.hideGroups = hideGroups;
	}

	public void setHideGifts(boolean hideGifts) {
		this.hideGifts = hideGifts;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public void setHideCountry(boolean hideCountry) {
		this.hideCountry = hideCountry;
	}

	public void setHideCitiy(boolean hideCitiy) {
		this.hideCitiy = hideCitiy;
	}

	public void setHideSex(boolean hideSex) {
		this.hideSex = hideSex;
	}

	public void setHidePhone(boolean hidePhone) {
		this.hidePhone = hidePhone;
	}

	private UserSettings(Builder builder) {
		userId = builder.userId;
		visiblePageOnlyFriends = builder.visiblePageOnlyFriends;
		hideFriends = builder.hideFriends;
		hideGroups = builder.hideGroups;
		hideGifts = builder.hideGifts;
		showDate = builder.showDate;
		hideCountry = builder.hideCountry;
		hideCitiy = builder.hideCitiy;
		hideSex = builder.hideSex;
		hidePhone = builder.hidePhone;
	}

	public static class Builder {

		public long		userId;
		private boolean	visiblePageOnlyFriends;
		private boolean	hideFriends;
		private boolean	hideGroups;
		private boolean	hideGifts;
		private String	showDate;
		private boolean	hideCountry;
		private boolean	hideCitiy;
		private boolean	hideSex;
		private boolean	hidePhone;

		public Builder setUserId(long userId) {
			this.userId = userId;
			return this;
		}

		public Builder setVisiblePageOnlyFriends(boolean visiblePageOnlyFriends) {
			this.visiblePageOnlyFriends = visiblePageOnlyFriends;
			return this;
		}

		public Builder setHideFriends(boolean hideFriends) {
			this.hideFriends = hideFriends;
			return this;
		}

		public Builder setHideGroups(boolean hideGroups) {
			this.hideGroups = hideGroups;
			return this;
		}

		public Builder setHideGifts(boolean hideGifts) {
			this.hideGifts = hideGifts;
			return this;
		}

		public Builder setShowDate(String showDate) {
			this.showDate = showDate;
			return this;
		}

		public Builder setHideCountry(boolean hideCountry) {
			this.hideCountry = hideCountry;
			return this;
		}

		public Builder setHideCitiy(boolean hideCitiy) {
			this.hideCitiy = hideCitiy;
			return this;
		}

		public Builder setHideSex(boolean hideSex) {
			this.hideSex = hideSex;
			return this;
		}

		public Builder setHidePhone(boolean hidePhone) {
			this.hidePhone = hidePhone;
			return this;
		}

		public UserSettings build() {
			return new UserSettings(this);
		}

	}

}
