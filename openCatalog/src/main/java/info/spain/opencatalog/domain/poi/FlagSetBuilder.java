package info.spain.opencatalog.domain.poi;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

public class FlagSetBuilder {

	private Set<Flag> flags = Sets.newTreeSet();

	public FlagSetBuilder add(FlagGroup group) {
		return add(group.getFlags());
	}
	
	public FlagSetBuilder add(Collection<Flag> flags) {
		this.flags.addAll(flags);
		return this;
	}
	
	public FlagSetBuilder without(Flag... flags) {
		for (Flag flag : flags) {
			this.flags.remove(flag);
		}
		return this;
	}

	public FlagSetBuilder add(Flag... flags) {
		for (Flag flag : flags) {
			this.flags.add(flag);
		}
		return this;
	}

	public Flag[] build() {
		return flags.toArray(new Flag[] {});
	}

	
}
